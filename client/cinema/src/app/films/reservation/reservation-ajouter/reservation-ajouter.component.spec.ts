import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationAjouterComponent } from './reservation-ajouter.component';

describe('ReservationAjouterComponent', () => {
  let component: ReservationAjouterComponent;
  let fixture: ComponentFixture<ReservationAjouterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationAjouterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationAjouterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
