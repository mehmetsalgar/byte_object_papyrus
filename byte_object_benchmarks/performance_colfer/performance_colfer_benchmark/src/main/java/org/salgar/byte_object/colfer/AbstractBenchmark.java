/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.salgar.byte_object.colfer;

import java.util.Calendar;
import java.util.Date;

public abstract class AbstractBenchmark {
    static final Long quantity = 45345345L;
    static final Integer productid = Integer.valueOf(446453453);
    static final String productName = "Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword; Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword";
    static final String productDescription = "Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber";
    static final Long prodcutPrice = Long.valueOf(234245356L);
    //private static final ProductStatus productStatus = ProductStatus.IN_STOCK;
    static final Long quantity2 = 636846L;
    static final Integer productid2 = Integer.valueOf(123123124);
    static final String productName2 = "Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest";
    static final String productDescription2 = "Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal";
    static final Long prodcutPrice2 = Long.valueOf(974545L);
    //private static final ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
    static final long orderId = 34535435L;
    static final Date now = new Date(System.currentTimeMillis());
    static final String error = "This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error!";
    static final String event = "This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event!";
    static final String message = "This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message!";
    static final Boolean processed = Boolean.TRUE;
    static final Integer stage = 62;
    static final Long addressId = Long.valueOf(654564L);
    static final String street = "Sesame Str";
    static final String houseNo = "11B";
    static final Integer plz = 64346;
    static final String city = "Atlantis";
    static final String country = "Neptune";
    static final Long customerId = 456467548L;
    static final String firstname = "Dirk";
    static final String lastname = "Pitt";
    static final Date birthDate = new Date(System.currentTimeMillis() - 1000000);
    static final Long billId = 1456356563L;
    static final Long amount = 4534579786L;
    static final Calendar calendar = Calendar.getInstance();
    static final Date latestPaymentDay = calendar.getTime();
    static final Long billId2 = 435345768L;
    static final Long amount2 = 87645653L;
    static final Calendar calendar2 = Calendar.getInstance();
    static final Date latestPaymentDay2 = calendar.getTime();
    static final Long id_reminder = 4744573L;
    //private static final ReminderRank rank = ReminderRank.INITIAL;
    static final Long interest = 5L;
    static final Long id_cashInstitutuion = 23423L;
    static final String nameCashInstitutuion = "VISA";

    static final Long id_address = Long.valueOf(654564L);
    static final String street1 = "Sesame Str";
    static final String houseNo1 = "11B";
    static final Integer plz1 = 64346;
    static final String city1 = "Atlantis";
    static final String country1 = "Neptune";

    static final Long id_reminder2 = 4744573L;
    //private static final ReminderRank rank2 = ReminderRank.INITIAL;
    static final Long interest2 = 5L;

    static final Integer identifierId = Integer.valueOf(6546543);
    static final String someText = "We are developing interesting things here!";

    static final Long c_addressId = Long.valueOf(654564L);
    static final String c_street = "Sesame Str";
    static final String c_houseNo = "11B";
    static final Integer c_plz = 64346;
    static final String c_city = "Atlantis";
    static final String c_country = "Neptune";

    static final Integer identifier2Id = Integer.valueOf(5445643);
    static final String someOtherText = "Big data here we are coming";

    static final Long c_addressId1 = Long.valueOf(2187234);
    static final String c_street1 = "One way to Hell Str";
    static final String c_houseNo1 = "999";
    static final Integer c_plz1 = 54331;
    static final String c_city1 = "Skovia";
    static final String c_country1 = "Harlamd Worl";

    //private static final String key = "Halli Hallo";
    //private static final String key2 = "All hail big data";

    static final Long c_quantity = 45345345L;

    static final Integer c_productid = Integer.valueOf(44644565);
    static final String c_productName = "Double Sided Sword";
    static final String c_productDescription = "Darth Maul's light saber";
    static final Long c_prodcutPrice = Long.valueOf(234245356L);
    //private static final ProductStatus c_productStatus = ProductStatus.IN_STOCK;

    static final Long c_quantity2 = 636846L;

    static final Integer c_productid2 = Integer.valueOf(123123124);
    static final String c_productName2 = "Tennis Racquest";
    static final String c_productDescription2 = "Stan the Animal";
    static final Long c_prodcutPrice2 = Long.valueOf(974545L);
    //private static final ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;

    static final Integer conntainerId = Integer.valueOf(92473864);
}