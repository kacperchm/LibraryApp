import {Address} from "./address.model";
import {LibraryMember} from "./library-member.model";
import {FormControl} from "@angular/forms";


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

export interface PostPasswordForm {
  oldPassword: FormControl<string>;
  newPassword: FormControl<string>;
}

export interface PostNumberForm {
  phoneNumber: FormControl<string>;
}

export interface PostRoleForm {
  role: FormControl<string>;
}

export interface Role {
  value: string;
  viewValue: string;
}
