import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NatureMissionListComponent } from './nature-mission-list.component';

describe('NatureMissionListComponent', () => {
  let component: NatureMissionListComponent;
  let fixture: ComponentFixture<NatureMissionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NatureMissionListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NatureMissionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
