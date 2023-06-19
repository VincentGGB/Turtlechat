/*
        TurtleChat
        Copyright (C) 2023  TurtleChat Open Source Community

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.slowchat.unit;

import com.slowchat.message.domain.models.MessageModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * Unit test for MessageModel class of message service
 */
public class MessageModelUnitTest {

    @Test
    public void shouldBeReceived() {
        //GIVEN
        MessageModel model = new MessageModel();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 1, 1, 0, 0, 0);
        model.setReceptionDate(calendar.getTime());

        //WHEN

        boolean output = model.isReceived();

        //THEN
        Assert.assertTrue(output);
    }

    @Test
    public void shouldBeNotReceived() {
        //GIVEN
        MessageModel model = new MessageModel();
        model.setReceptionDate(null);

        //WHEN
        boolean output = model.isReceived();

        //THEN
        Assert.assertFalse(output);
    }

    @Test
    public void shouldBeExpired() {
        //GIVEN
        MessageModel model = new MessageModel();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 1, 1, 0, 0, 0);
        model.setSentDate(calendar.getTime());

        //WHEN
        Boolean output = model.isExpired();

        //THEN
        Assert.assertTrue(output);
    }

    @Test
    public void shouldNotBeExpired() {
        //GIVEN
        MessageModel model = new MessageModel();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 1, 1, 0, 0, 0);
        model.setSentDate(calendar.getTime());

        //WHEN
        Boolean output = model.isExpired();

        //THEN
        Assert.assertFalse(output);
    }

    @Test
    public void shouldBeForMe() {
        //GIVEN
        MessageModel model = new MessageModel();
        model.setIdReceiver("me");

        //WHEN
        Boolean output = model.isFor("me");

        //THEN
        Assert.assertTrue(output);
    }

    @Test
    public void shouldNotBeForMe() {
        //GIVEN
        MessageModel model = new MessageModel();
        model.setIdReceiver("me");

        //WHEN
        Boolean output = model.isFor("noteMe");

        //THEN
        Assert.assertFalse(output);
    }
    @Test
    public void shouldBeByMe() {
        //GIVEN
        MessageModel model = new MessageModel();
        model.setIdSender("me");

        //WHEN
        Boolean output = model.isBy("me");

        //THEN
        Assert.assertTrue(output);
    }

    @Test
    public void shouldNotBeByMe() {
        //GIVEN
        MessageModel model = new MessageModel();
        model.setIdSender("me");

        //WHEN
        Boolean output = model.isBy("noteMe");

        //THEN
        Assert.assertFalse(output);
    }
}
