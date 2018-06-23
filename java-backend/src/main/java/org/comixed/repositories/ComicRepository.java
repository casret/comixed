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

package org.comixed.repositories;

import java.util.Date;
import java.util.List;

import org.comixed.library.model.Comic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends
                                 CrudRepository<Comic,
                                                Long>
{
    /**
     * Finds all comics added after the specified date
     *
     * @param after
     *            the cutoff date
     * @return the list of comics
     */
    List<Comic> findByDateAddedGreaterThan(Date after);

    /**
     * Finds a comic based on filename.
     *
     * @param filename
     *            the filename
     * @return the comic
     */
    Comic findByFilename(String filename);

    /**
     * Finds all unread comics
     *
     * @param after
     *            the cutoff date
     * @return the list of comics
     */
    List<Comic> findByLastReadDateIsNullOrderByDateAddedDesc();
}
