import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {map, Observable} from "rxjs";
import {BorrowDetails, BorrowResponse, GetBorrowsResponse} from "../models/borrow.model";

@Injectable({
  providedIn: 'root'
})
export class BorrowsService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient,
              private router: Router,) {
  }

  getBorrowedBooks(
    id: number,
    pageIndex: number,
    itemPerPage: number,
    sortDirection: string,
    sortColumnName: string,
  ): Observable<GetBorrowsResponse> {
    let params = new HttpParams()
      .append('page', pageIndex)
      .append('limit', itemPerPage);
    if (sortColumnName) {
      params = params
        .append('sort', sortColumnName)
        .append('order', sortDirection);
    }

    return this.http.get<BorrowResponse[]>(`${this.apiUrl}/all/borrows/${id}`, {
      observe: 'response',
      params,
    })
      .pipe(
        map((response) => {
          if (!Array.isArray(response.body)) {
            return {borrows: [], totalCount: 0};
          }

          const arr: BorrowDetails[] = response.body;
          const totalCount = Number(response.headers.get("X-Total-Count"));
          return {borrows: arr, totalCount: totalCount};
        })
      )
  }

  getQuantityOfBorrows(id: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/all/borrows/size/${id}`).pipe(
      map((value) => {
        return value;
      })
    )
  }

  returnBook(borrowId:number): Observable<BorrowDetails> {
    let params = new HttpParams()
      .append('borrowId', borrowId)
    return this.http.post<BorrowResponse>(`${this.apiUrl}/borrow/return`, params)
      .pipe(
        map((borrow) => {
          return borrow
        })
      )
  }

  borrowBook( bookId:number, userId:number): Observable<BorrowDetails> {
    let params = new HttpParams()
      .append('bookId', bookId)
      .append('userId', userId)
    return this.http.post<BorrowResponse>(`${this.apiUrl}/borrow`, params)
      .pipe(
        map((borrow) => {
          return borrow
        })
      )
  }
}
