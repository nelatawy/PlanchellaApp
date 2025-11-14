import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchBoxMain } from './search-box-main';

describe('SearchBoxMain', () => {
  let component: SearchBoxMain;
  let fixture: ComponentFixture<SearchBoxMain>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchBoxMain]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchBoxMain);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
