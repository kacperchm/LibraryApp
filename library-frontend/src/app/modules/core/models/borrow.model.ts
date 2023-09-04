import {LibraryMember} from "./library-member.model";
import {Book} from "./book.model";

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
