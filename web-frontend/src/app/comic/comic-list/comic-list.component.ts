import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {Comic} from '../comic.model';
import {ComicService} from '../comic.service';
import {ComicListEntryComponent} from '../comic-list-entry/comic-list-entry.component';

@Component({
  selector: 'app-comic-list',
  templateUrl: './comic-list.component.html',
  styleUrls: ['./comic-list.component.css'],
  providers: [ComicService],
})
export class ComicListComponent implements OnInit {
  private comics: Comic[];
  private all_series: string[];
  private current_comic: Comic;
  private sort_options: any[] = [
    {id: 0, label: 'Default'},
    {id: 1, label: 'Sort by series'},
    {id: 2, label: 'Sort by added date'},
    {id: 3, label: 'Sort by cover date'},
    {id: 4, label: 'Sort by last read date'},
  ];

  constructor(private router: Router, private comicService: ComicService) {}

  ngOnInit() {
    this.getAllComics();
    this.comicService.current_comic.subscribe(
      (comic: Comic) => {
        this.current_comic = comic;
      });
  }

  getImageURL(comic: Comic): string {
    if (comic.missing == true) {
      return this.comicService.getMissingImageUrl();
    } else {
      return this.comicService.getImageUrl(comic.id, 0);
    }
  }

  setSortOption(sort_id: any): void {
    this.comics.sort((comic1: Comic, comic2: Comic) => {

      let left: any;
      let right: any;

      switch (parseInt(sort_id, 10)) {
        case 1: left = comic1.series; right = comic2.series; break;
        case 2: left = comic1.added_date; right = comic2.added_date; break;
        case 3: left = comic1.cover_date; right = comic2.cover_date; break;
        case 4: left = comic1.last_read_date; right = comic2.last_read_date; break;
        default: left = comic1.id; right = comic2.id; break;
      }

      if (left < right) {return -1;}
      if (left > right) {return 1;}
      return 0;
    });
  }

  getTitleTextFor(comic: Comic): string {
    let result = comic.series || comic.filename;

    if (comic.issue_number != null) {
      result = result + ' #' + comic.issue_number;
    }
    if (comic.volume != null) {
      result = result + ' (v' + comic.volume + ')';
    }

    return result;
  }

  getAllComics(): void {
    this.comicService.findAll().subscribe(
      comics => {
        this.comics = comics;
        this.all_series = new Array();
        this.comics.forEach((comic: Comic) => {
          this.all_series.push(comic.series || comic.filename);
        });
      },
      err => {
        console.log(err);
      }
    )
  }

  redirectAddComicPage() {
    this.router.navigate(['/comic/add']);
  }

  editComicPage(comic: Comic) {
    if (comic) {
      console.log('Edit comic: id=' + comic.id + " filename=" + comic.filename);
    }
  }

  deleteComic(comic: Comic) {
    if (comic) {
      console.log('Delete comic: id=' + comic.id + " filename=" + comic.filename);
    }
  }


}
