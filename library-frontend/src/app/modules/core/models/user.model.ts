import {Address} from "./address.model";
import {LibraryMember} from "./library-member.model";


export interface User {
  id: number;
  username: string;
  mail: string;
  phoneNumber: string;
  password: string;
  role: string;
  address: Address;
  libraryMember: LibraryMember;
}

export interface UserResponse {
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

export interface UserLoginData {
  email: string;
  password: string;
}

export class User {
  constructor(id: number,
              username: string,
              mail: string,
              phoneNumber: string,
              password: string,
              role: string) {
  }
}

export class RegisterUser {
  constructor(username: string,
              mail: string,
              phoneNumber: string,
              password: string,
              city: string,
              zipCode: string,
              street: string,
              houseNumber: string,
              name: string,
              surname: string
  ) {
  }
}

export class UserDetails {
  constructor(id: number,
              username: string,
              mail: string,
              phoneNumber: string,
              password: string,
              role: string,
              addressId: number,
              city: string,
              zipCode: string,
              street: string,
              houseNumber: string,
              memberId: number,
              name: string,
              surname: string,
              numOfBorrowedBooks: number,
              blockade: boolean) {
  }
}

export interface GetUsersResponse {
  users: UserDetails[];
  totalCount: number;
}
