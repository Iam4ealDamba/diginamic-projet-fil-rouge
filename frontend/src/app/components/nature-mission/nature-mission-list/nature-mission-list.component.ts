// nature-mission-list.component.ts
import { Component, OnInit } from '@angular/core';
import { NatureMissionService } from '../../../services/nature-mission.service';
import { NatureMission } from '../../../models/nature-mission.model';


@Component({
  selector: 'app-nature-mission-list',
  templateUrl: './nature-mission-list.component.html',
  styleUrls: ['./nature-mission-list.component.css']
})
export class NatureMissionListComponent implements OnInit {
  natureMissions: NatureMission[] = [];

  constructor(private natureMissionService: NatureMissionService) {}

  ngOnInit(): void {
    this.natureMissionService.getNatureMissions().subscribe(data => {
      this.natureMissions = data;
    });
  }
}
