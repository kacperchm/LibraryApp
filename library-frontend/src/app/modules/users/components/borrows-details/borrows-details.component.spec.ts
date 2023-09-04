import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowsDetailsComponent } from './borrows-details.component';

describe('BorrowsDetailsComponent', () => {
  let component: BorrowsDetailsComponent;
  let fixture: ComponentFixture<BorrowsDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BorrowsDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
