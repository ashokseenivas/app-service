import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving: boolean;

  countries: ICountry[];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    phone: [],
    password: [],
    isAgreeTerms: [],
    isAgreePolicy: [],
    createdDate: [],
    updatedDate: [],
    activatedDate: [],
    status: [],
    country: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerService: CustomerService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
    this.countryService
      .query({ filter: 'customer-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICountry[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICountry[]>) => response.body)
      )
      .subscribe(
        (res: ICountry[]) => {
          if (!this.editForm.get('country').value || !this.editForm.get('country').value.id) {
            this.countries = res;
          } else {
            this.countryService
              .find(this.editForm.get('country').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICountry>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICountry>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICountry) => (this.countries = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(customer: ICustomer) {
    this.editForm.patchValue({
      id: customer.id,
      firstName: customer.firstName,
      lastName: customer.lastName,
      email: customer.email,
      phone: customer.phone,
      password: customer.password,
      isAgreeTerms: customer.isAgreeTerms,
      isAgreePolicy: customer.isAgreePolicy,
      createdDate: customer.createdDate != null ? customer.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: customer.updatedDate != null ? customer.updatedDate.format(DATE_TIME_FORMAT) : null,
      activatedDate: customer.activatedDate != null ? customer.activatedDate.format(DATE_TIME_FORMAT) : null,
      status: customer.status,
      country: customer.country
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      phone: this.editForm.get(['phone']).value,
      password: this.editForm.get(['password']).value,
      isAgreeTerms: this.editForm.get(['isAgreeTerms']).value,
      isAgreePolicy: this.editForm.get(['isAgreePolicy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      updatedDate:
        this.editForm.get(['updatedDate']).value != null ? moment(this.editForm.get(['updatedDate']).value, DATE_TIME_FORMAT) : undefined,
      activatedDate:
        this.editForm.get(['activatedDate']).value != null
          ? moment(this.editForm.get(['activatedDate']).value, DATE_TIME_FORMAT)
          : undefined,
      status: this.editForm.get(['status']).value,
      country: this.editForm.get(['country']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCountryById(index: number, item: ICountry) {
    return item.id;
  }
}
