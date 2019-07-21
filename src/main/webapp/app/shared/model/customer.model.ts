import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  password?: string;
  isAgreeTerms?: boolean;
  isAgreePolicy?: boolean;
  createdDate?: Moment;
  updatedDate?: Moment;
  activatedDate?: Moment;
  status?: boolean;
  country?: ICountry;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phone?: string,
    public password?: string,
    public isAgreeTerms?: boolean,
    public isAgreePolicy?: boolean,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public activatedDate?: Moment,
    public status?: boolean,
    public country?: ICountry
  ) {
    this.isAgreeTerms = this.isAgreeTerms || false;
    this.isAgreePolicy = this.isAgreePolicy || false;
    this.status = this.status || false;
  }
}
