import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationModifierComponent } from './reservation-modifier.component';

describe('ReservationModifierComponent', () => {
  let component: ReservationModifierComponent;
  let fixture: ComponentFixture<ReservationModifierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationModifierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationModifierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
