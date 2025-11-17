import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunitySelector } from './community-selector';

describe('CommunitySelector', () => {
  let component: CommunitySelector;
  let fixture: ComponentFixture<CommunitySelector>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommunitySelector]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommunitySelector);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
