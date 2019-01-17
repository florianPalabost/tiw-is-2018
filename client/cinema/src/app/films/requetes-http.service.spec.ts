import { TestBed } from '@angular/core/testing';

import { RequetesHTTPService } from './requetes-http.service';

describe('RequetesHTTPService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RequetesHTTPService = TestBed.get(RequetesHTTPService);
    expect(service).toBeTruthy();
  });
});
