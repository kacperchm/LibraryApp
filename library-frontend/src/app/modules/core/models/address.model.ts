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
