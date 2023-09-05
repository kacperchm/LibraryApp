import {FormControl} from "@angular/forms";

export interface BookResponse {
  id: number;
  author: string;
  title: string;
  publicationYear: number;
  category: string;
  availability: boolean;
}

export class Book{
  constructor(
    public id: number,
    public author: string,
    public title: string,
    public publicationYear: number,
    public category: string,
    public availability: boolean,
  ) {
  }}

export interface GetBooksResponse{
  books: Book[];
  totalCount: number;
}

export interface PostBookForm {
  author: FormControl<string>;
  title: FormControl<string>;
  publicationYear: FormControl<number>;
  category: FormControl<string>;
}

export interface Category {
  value: string;
  viewValue: string;
}
