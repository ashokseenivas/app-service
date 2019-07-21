import { NgModule } from '@angular/core';

import { BbxCustomeronboardingSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [BbxCustomeronboardingSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [BbxCustomeronboardingSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BbxCustomeronboardingSharedCommonModule {}
