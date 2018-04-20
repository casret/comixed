import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportComicsComponent } from './import-comics.component';

describe('ImportComicsComponent', () => {
  let component: ImportComicsComponent;
  let fixture: ComponentFixture<ImportComicsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImportComicsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportComicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
