import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {map, Observable} from "rxjs";
import {Book, BookResponse, GetBooksResponse} from "../models/book.model";
import {GetUsersResponse, UserDetails, UserResponse} from "../models/user.model";
import {Address} from "../models/address.model";
import {LibraryMember} from "../models/library-member.model";

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient,
              private router: Router) {
  }

  getUsers(
    pageIndex: number,
    itemPerPage: number,
    sortDirection: string,
    sortColumnName: string,
  ): Observable<GetUsersResponse> {
    let params = new HttpParams()
      .append('page', pageIndex)
      .append('limit', itemPerPage);
    if (sortColumnName) {
      params = params
        .append('sort', sortColumnName)
        .append('order', sortDirection);
    }

    return this.http
      .get<UserResponse[]>(`${this.apiUrl}/users/details`, {
        observe: 'response',
        params,
      })
      .pipe(
        map((response) => {
          console.log(response)
          if (!response.body) return {users: [], totalCount: 0};

          const arr: UserDetails[] = response.body.map(
            ({
               id, username, mail, phoneNumber, password, role, addressId,
               city, zipCode, street, houseNumber, memberId, name, surname,
               numOfBorrowedBooks, blockade
             }) => {
              return new UserDetails(
                id, username, mail, phoneNumber, password, role, addressId, city, zipCode, street, houseNumber,
                memberId, name, surname, numOfBorrowedBooks, blockade)
            }
          )

          const totalCount = Number(response.headers.get("X-Total-Count"));
          console.log(arr)
          return {users: arr, totalCount: totalCount};
        }),
      );
  }

  getQuantityOfUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/users/size`).pipe(
      map((value) => {
        console.log(value)
        return value;
      })
    )
  }

  getFilteredUsers(
    pageIndex: number,
    itemPerPage: number,
    sortDirection: string,
    sortColumnName: string,
    value: string,
  ): Observable<GetUsersResponse> {
    let params = new HttpParams()
      .append('page', pageIndex)
      .append('limit', itemPerPage);
    if (sortColumnName) {
      params = params
        .append('sort', sortColumnName)
        .append('order', sortDirection);
    } else {
      params = params
        .append('sort', 'ASC')
        .append('order', 'title');
    }
    if (value) {
      params = params
        .append('filter', value)
    }


    return this.http
      .get<UserResponse[]>(`${this.apiUrl}/users/filtered-details`, {
        observe: 'response',
        params,
      })
      .pipe(
        map((response) => {
          if (!response.body) return {users: [], totalCount: 0};

          const usersArr: UserDetails[] = response.body.map(
            ({
               id,
               username,
               mail,
               phoneNumber,
               password,
               role,
               addressId,
               city,
               zipCode,
               street,
               houseNumber,
               memberId,
               name,
               surname,
               numOfBorrowedBooks,
               blockade
             }) => {
              return new UserDetails(
                id,
                username,
                mail,
                phoneNumber,
                password,
                role,
                addressId,
                city,
                zipCode,
                street,
                houseNumber,
                memberId,
                name,
                surname,
                numOfBorrowedBooks,
                blockade)
            }
          )

          const totalCount = Number(response.headers.get("X-Total-Count"));
          console.log(usersArr)
          return {users: usersArr, totalCount: totalCount};
        }),
      );
  }

  getQuantityOfFilteredUsers(value: string): Observable<number> {
    let params = new HttpParams()

    params = params
      .append('filter', value)

    return this.http.get<number>(`${this.apiUrl}/users/size-filtered`, {params}).pipe(
      map((value) => {
        return value;
      })
    )
  }
}
