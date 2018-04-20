import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ComicRoutingModule } from './comic-routing.module';
import { ComicListComponent } from './comic-list/comic-list.component';
import { ImportComicsComponent } from './import-comics/import-comics.component';

@NgModule({
  imports: [
    CommonModule,
    ComicRoutingModule
  ],
  declarations: [ComicListComponent, ImportComicsComponent]
})
export class ComicModule { }
