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
