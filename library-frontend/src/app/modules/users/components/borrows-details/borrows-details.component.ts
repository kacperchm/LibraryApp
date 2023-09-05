import {AfterViewInit, Component, OnDestroy, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {BehaviorSubject, delay, filter, map, merge, Observer, startWith, Subscription, switchMap} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {BorrowDetails} from "../../../core/models/borrow.model";
import {BorrowsService} from "../../../core/services/borrows.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UsersService} from "../../../core/services/users.service";

@Component({
  selector: 'app-borrows-details',
  templateUrl: './borrows-details.component.html',
  styleUrls: ['./borrows-details.component.css']
})
export class BorrowsDetailsComponent implements AfterViewInit, OnDestroy {
  memberId!: number;
  userId!: number;
  displayedColumns: string[] = [
    'lp',
    'author',
    'title',
    'borrowDate',
    'returnDate',
    'returned',
    'buttons'
  ];
  dataSource!: MatTableDataSource<BorrowDetails>;
  totalCount = 0;
  sub = new Subscription();
  errorMessage = '';
  memberIdSubject = new BehaviorSubject<number | null>(null);
  observer: Observer<unknown> = {
    next: (borrow) => {
      this.errorMessage = '';
      this.router.navigate([`/user-details/${this.userId}`]);
    },
    error: (err) => {
      this.errorMessage = 'Wystąpił błąd';
    },
    complete: () => {
    },
  };

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private borrowsService: BorrowsService,
              private route: ActivatedRoute,
              private usersService: UsersService,
              private router: Router) {
    this.memberIdSubject
      .pipe(
        filter(memberId => memberId !== null),
        delay(1000)
      )
      .subscribe(memberId => {
        if (memberId != null) {
          this.initAfterDelay(memberId);
        }
      });
  }

  ngAfterViewInit(): void {
    this.route.params.subscribe(params => {
      this.memberId = +params['memberId'];
      this.userId = +params['id'];
      this.memberIdSubject.next(this.memberId);
    });
  }


  initAfterDelay(mId: number): void {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    this.borrowsService.getQuantityOfBorrows(mId)
      .pipe(
      )
      .subscribe({
        next: value => {
          this.totalCount = value;
        }
      });

    this.sub.add(
      merge(this.sort.sortChange, this.paginator.page)
        .pipe(
          startWith({}),
          switchMap(() => {
            const pageIndex = this.paginator.pageIndex;
            const itemsPerPage = this.paginator.pageSize;
            const sortDirection = this.sort.direction;
            const sortColumnName = this.sort.active;

            return this.borrowsService.getBorrowedBooks(
              mId,
              pageIndex,
              itemsPerPage,
              sortDirection,
              sortColumnName,
            );
          }),
          map((data) => {
            return data.borrows;
          }),
        )
        .subscribe((borrows) => {
          this.dataSource = new MatTableDataSource<BorrowDetails>(borrows);
        }),
    );
  }

  returnBook(borrowId: number) {
    this.borrowsService
      .returnBook(borrowId)
      .subscribe(this.observer)
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }


}
