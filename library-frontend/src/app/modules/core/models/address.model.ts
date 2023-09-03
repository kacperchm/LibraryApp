import {FormControl} from "@angular/forms";

export interface Address {
  id: number;
  city: string;
  zipCode: string;
  street: string;
  houseNumber: string;
}

export class Address {
  constructor(id: number,
              city: string,
              zipCode: string,
              street: string,
              houseNumber: string) {
  }
}

export interface PostAddressForm {
  city: FormControl<string>,
  zipCode: FormControl<string>,
  street: FormControl<string>,
  houseNumber: FormControl<string>
}
