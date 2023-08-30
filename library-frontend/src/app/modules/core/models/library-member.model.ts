export interface LibraryMember {
  id: number;
  name: string;
  surname: string;
  numOfBorrowedBooks: number;
  blockade: boolean;
}

export class LibraryMember {
  constructor(id: number,
              name: string,
              surname: string,
              numOfBorrowedBooks: number,
              blockade: boolean) {
  }
}

