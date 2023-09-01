import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {map, Observable} from "rxjs";
import {environment} from "../../../../environments/environment.development";
import {Book, BookResponse, GetBooksResponse} from "../models/book.model";

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient,
              private router: Router,) {
  }

  getCategory(): Observable<String[]> {
    return this.http.get<String[]>(`${this.apiUrl}/book/categories`).pipe(
      map((categories) => {
        return categories;
      }))
  }

  getBooks(
    pageIndex: number,
    itemPerPage: number,
    sortDirection: string,
    sortColumnName: string,
    value = '',
  ): Observable<GetBooksResponse> {
    let params = new HttpParams()
      .append('page', pageIndex)
      .append('limit', itemPerPage);
    if (sortColumnName) {
      params = params
        .append('sort', sortColumnName)
        .append('order', sortDirection);
    }

    return this.http
      .get<BookResponse[]>(`${this.apiUrl}/book/all`, {
        observe: 'response',
        params,
      })
      .pipe(
        map((response) => {
          if (!response.body) return {books: [], totalCount: 0};

          const clientsArr: Book[] = response.body.map(
            ({id, author, title, publicationYear, category, availability}) =>
              new Book(
                id,
                author,
                title,
                publicationYear,
                category,
                availability
              ),
          );

          const totalCount = Number(response.headers.get("X-Total-Count"));
          return {books: clientsArr, totalCount: totalCount};
        }),
      );
  }

  getQuantityOfBooks(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/book/size`).pipe(
      map((value) => {
        console.log(value)
        return value;
      })
    )
  }

  getFilteredBooks(
    pageIndex: number,
    itemPerPage: number,
    sortDirection: string,
    sortColumnName: string,
    value: string,
    category: string,
  ): Observable<GetBooksResponse> {
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
      params = params
        .append('category', category)


    return this.http
      .get<BookResponse[]>(`${this.apiUrl}/book/filter`, {
        observe: 'response',
        params,
      })
      .pipe(
        map((response) => {
          if (!response.body) return {books: [], totalCount: 0};

          const clientsArr: Book[] = response.body.map(
            ({id, author, title, publicationYear, category, availability}) =>
              new Book(
                id,
                author,
                title,
                publicationYear,
                category,
                availability
              ),
          );

          const totalCount = Number(response.headers.get("X-Total-Count"));
          return {books: clientsArr, totalCount: totalCount};
        }),
      );
  }

  getQuantityOfFilteredBooks(value: string, category: string): Observable<number> {
    let params = new HttpParams()

      params = params
        .append('filter', value)

      params = params
        .append('category', category)

    return this.http.get<number>(`${this.apiUrl}/book/size-filtered`, {params}).pipe(
      map((value) => {
        console.log(value)
        return value;
      })
    )
  }
}
