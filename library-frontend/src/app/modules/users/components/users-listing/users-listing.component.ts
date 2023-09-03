import {AfterViewInit, Component, OnDestroy, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {FormControl} from "@angular/forms";
import {map, merge, startWith, Subscription, switchMap} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {UserDetails} from "../../../core/models/user.model";
import {UsersService} from "../../../core/services/users.service";

@Component({
  selector: 'app-users-listing',
  templateUrl: './users-listing.component.html',
  styleUrls: ['./users-listing.component.css']
})
export class UsersListingComponent  implements AfterViewInit, OnDestroy {
  displayedColumns: string[] = [
    'lp',
    'username',
    'mail',
    'phoneNumber',
    'buttons'
  ];
  dataSource!: MatTableDataSource<UserDetails>;
  totalCount = 0;
  filterValue = new FormControl('', { nonNullable: true });
  sub = new Subscription();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private usersService: UsersService) {}

  ngAfterViewInit(): void {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    this.usersService.getQuantityOfUsers().subscribe({
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

            if(this.filterValue.value !== '') {
              return this.usersService.getFilteredUsers(
                pageIndex,
                itemsPerPage,
                sortDirection,
                sortColumnName,
                this.filterValue.value)
            } else {
              return this.usersService.getUsers(
                pageIndex,
                itemsPerPage,
                sortDirection,
                sortColumnName,
              );
            }

          }),
          map((data) => {
            return data.users;
          }),
        )
        .subscribe((users) => {
          console.log(users)
          this.dataSource = new MatTableDataSource<UserDetails>(users);
        }),
    );
  }

  onFilterSelectionChange() {
    const pageIndex = this.paginator.pageIndex;
    const itemsPerPage = this.paginator.pageSize;
    const sortDirection = this.sort.direction;
    const sortColumnName = this.sort.active;


    this.usersService
      .getFilteredUsers(pageIndex, itemsPerPage, sortDirection, sortColumnName, this.filterValue.value)
      .subscribe({
        next: (resp) => {
          this.dataSource = new MatTableDataSource<UserDetails>(resp.users);
        },
      });

    this.usersService.getQuantityOfFilteredUsers(this.filterValue.value).subscribe({
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

    this.usersService
      .getFilteredUsers(pageIndex, itemsPerPage, sortDirection, sortColumnName, val)
      .subscribe({
        next: (resp) => {
          this.dataSource = new MatTableDataSource<UserDetails>(resp.users);

        },
      });

    this.usersService.getQuantityOfFilteredUsers(this.filterValue.value).subscribe({
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
