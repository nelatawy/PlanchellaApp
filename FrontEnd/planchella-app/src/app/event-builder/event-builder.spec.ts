import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventBuilder } from './event-builder';

describe('EventBuilder', () => {
  let component: EventBuilder;
  let fixture: ComponentFixture<EventBuilder>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventBuilder]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventBuilder);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
