import {LibraryMember} from "./library-member.model";
import {Book} from "./book.model";
import {FormControl} from "@angular/forms";

export interface Borrow {
  id: number;
  member: LibraryMember;
  book: Book;
  borrowDate: Date;
  returnDate: Date;
  returned: boolean;
}

export interface BorrowResponse {
  id: number;
  author: string;
  title: string;
  borrowDate: string;
  returnDate: string;
  returned: boolean;
}

export class BorrowDetails {
  constructor(id: number,
              author: string,
              title: string,
              borrowDate: string,
              returnDate: string,
              returned: boolean) {
  }
}

export interface GetBorrowsResponse{
  borrows: BorrowDetails[];
  totalCount: number;
}

export interface PostBorrowForm {
  user: FormControl<number>;
}

export interface BorrowUserVal {
  id: number;
  username: string;
  mail: string;
  phoneNumber: string;
  password: string;
  role: string;
  addressId: number;
  city: string;
  zipCode: string;
  street: string;
  houseNumber: string;
  memberId: number;
  name: string;
  surname: string;
  numOfBorrowedBooks: number;
  blockade: boolean;
}
