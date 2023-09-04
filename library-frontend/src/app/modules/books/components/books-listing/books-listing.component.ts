import {AfterViewInit, Component, OnChanges, OnDestroy, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {Book} from "../../../core/models/book.model";
import {BooksService} from "../../../core/services/books.service";
import {MatTableDataSource} from "@angular/material/table";
import {FormControl} from "@angular/forms";
import {debounceTime, distinctUntilChanged, map, merge, startWith, Subscription, switchMap} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-books-listing',
  templateUrl: './books-listing.component.html',
  styleUrls: ['./books-listing.component.css']
})
export class BooksListingComponent implements AfterViewInit, OnDestroy, OnInit {
  displayedColumns: string[] = [
    'lp',
    'author',
    'title',
    'publicationYear',
    'availability'
  ];
  dataSource!: MatTableDataSource<Book>;
  totalCount = 0;
  filterValue = new FormControl('', { nonNullable: true });
  sub = new Subscription();
  categories !: String[];
  selectedCategoryControl = new FormControl('', {nonNullable:true});

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private booksService: BooksService) {}

  ngOnInit(): void {
        this.booksService.getCategory().subscribe({
          next: cat => {
            this.categories = cat;
          }
        })
    }

  ngAfterViewInit(): void {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

      this.booksService.getQuantityOfBooks().subscribe({
        next: value => {
          this.totalCount = value;
        }
      })


    this.sub.add(
      merge(this.sort.sortChange, this.paginator.page)
        .pipe(
          startWith({}),
          switchMap(() => {
            const pageIndex = this.paginator.pageIndex;
            const itemsPerPage = this.paginator.pageSize;
            const sortDirection = this.sort.direction;
            const sortColumnName = this.sort.active;

            if(this.selectedCategoryControl.value !== '' || this.filterValue.value !== '') {
              return this.booksService.getFilteredBooks(
                pageIndex,
                itemsPerPage,
                sortDirection,
                sortColumnName,
                this.filterValue.value,
                this.selectedCategoryControl.value)
            } else {
              return this.booksService.getBooks(
                pageIndex,
                itemsPerPage,
                sortDirection,
                sortColumnName,
              );
            }

          }),
          map((data) => {
            return data.books;
          }),
        )
        .subscribe((books) => {
          this.dataSource = new MatTableDataSource<Book>(books);
        }),
    );
  }

  onCategorySelectionChange() {
    const pageIndex = this.paginator.pageIndex;
    const itemsPerPage = this.paginator.pageSize;
    const sortDirection = this.sort.direction;
    const sortColumnName = this.sort.active;


    this.booksService
      .getFilteredBooks(pageIndex, itemsPerPage, sortDirection, sortColumnName, this.filterValue.value, this.selectedCategoryControl.value)
      .subscribe({
        next: (resp) => {
          this.dataSource = new MatTableDataSource<Book>(resp.books);
        },
      });

    this.booksService.getQuantityOfFilteredBooks(this.filterValue.value, this.selectedCategoryControl.value).subscribe({
      next: value => {
        this.totalCount = value;
      }
    })

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  applyFilter(val: string, category: string) {
    const pageIndex = this.paginator.pageIndex;
    const itemsPerPage = this.paginator.pageSize;
    const sortDirection = this.sort.direction;
    const sortColumnName = this.sort.active;

    this.booksService
      .getFilteredBooks(pageIndex, itemsPerPage, sortDirection, sortColumnName, val, category)
      .subscribe({
        next: (resp) => {
          this.dataSource = new MatTableDataSource<Book>(resp.books);

        },
      });

    this.booksService.getQuantityOfFilteredBooks(this.filterValue.value, this.selectedCategoryControl.value).subscribe({
      next: value => {
        this.totalCount = value;
      }
    })

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
