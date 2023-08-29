import {Borrow} from "./borrow.model";

export interface LibraryMember {
  id: number;
  name: string;
  surname: string;
  numOfBorrowedBooks: number;
  blockade: boolean;
  borrowList: Borrow[];
}

