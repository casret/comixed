/*
 * ComixEd - A digital comic book library management application.
 * Copyright (C) 2017, Darryl L. Pierce
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.package
 * org.comixed;
 */

package org.comixed.web.controllers;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;

import org.springframework.core.io.InputStreamResource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.comixed.library.model.Comic;
import org.comixed.library.model.OPDSAcquisitionFeed;
import org.comixed.library.model.OPDSFeed;
import org.comixed.library.model.OPDSNavigationFeed;
import org.comixed.library.model.View;
import org.comixed.repositories.ComicRepository;

@RestController
@RequestMapping(value = "/api/opds",
                produces =
                {"application/atom+xml"})
public class OPDSController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin
    public OPDSFeed get() throws ParseException
    {
        OPDSFeed feed = new OPDSNavigationFeed(this.messageSource.getMessage("opds.start.title", null,
                                                                             Locale.getDefault()));

        return feed;
    }

    @RequestMapping(value = "/all",
                    method = RequestMethod.GET)
    @CrossOrigin
    public OPDSFeed all() throws ParseException
    {
        return new OPDSAcquisitionFeed("/api/opds/all",
                                       this.messageSource.getMessage("opds.all.title", null, Locale.getDefault()),
                                       this.comicRepository.findAll());
    }

    @RequestMapping(value = "/unread",
                    method = RequestMethod.GET)
    @CrossOrigin
    public OPDSFeed unread() throws ParseException
    {
        return new OPDSAcquisitionFeed("/api/opds/unread",
                                       this.messageSource.getMessage("opds.unread.title", null, Locale.getDefault()),
                                       this.comicRepository.findByLastReadDateIsNullOrderByDateAddedDesc());
    }
}
