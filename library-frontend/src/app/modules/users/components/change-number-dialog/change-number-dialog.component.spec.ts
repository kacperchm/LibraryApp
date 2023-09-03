import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeNumberDialogComponent } from './change-number-dialog.component';

describe('ChangeNumberDialogComponent', () => {
  let component: ChangeNumberDialogComponent;
  let fixture: ComponentFixture<ChangeNumberDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeNumberDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangeNumberDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
