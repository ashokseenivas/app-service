import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BbxCustomeronboardingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BbxCustomeronboardingSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BbxCustomeronboardingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BbxCustomeronboardingSharedModule {
  static forRoot() {
    return {
      ngModule: BbxCustomeronboardingSharedModule
    };
  }
}
