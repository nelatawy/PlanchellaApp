import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StaredPostsPage } from './stared-posts-page';

describe('StaredPostsPage', () => {
  let component: StaredPostsPage;
  let fixture: ComponentFixture<StaredPostsPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StaredPostsPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StaredPostsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
