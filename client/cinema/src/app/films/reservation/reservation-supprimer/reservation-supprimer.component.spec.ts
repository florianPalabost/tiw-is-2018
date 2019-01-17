import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationSupprimerComponent } from './reservation-supprimer.component';

describe('ReservationSupprimerComponent', () => {
  let component: ReservationSupprimerComponent;
  let fixture: ComponentFixture<ReservationSupprimerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationSupprimerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationSupprimerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
