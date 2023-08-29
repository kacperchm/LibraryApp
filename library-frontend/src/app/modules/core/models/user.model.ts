import {Address, } from "./address.model";
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