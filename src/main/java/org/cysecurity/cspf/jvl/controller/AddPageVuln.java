/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cysecurity.cspf.jvl.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.coyote.ajp.AjpAprProtocol;
import io.undertow.server.protocol.ajp.*;

import java.io.*;

/**
 *
 * @author breakthesec
 */
public class AddPageVuln {


    public AddPageVuln() {
        AjpAprProtocol x = new AjpAprProtocol();
        x.setRequiredSecret("sdfdsf");
        AjpRequestParser t = new AjpRequestParser("a", true, 0, 0, false);
        t.parse(null, null, null);
    }
}
