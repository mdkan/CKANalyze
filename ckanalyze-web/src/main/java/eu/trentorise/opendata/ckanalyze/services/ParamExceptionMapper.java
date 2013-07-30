/**
* *****************************************************************************
* Copyright 2012-2013 Trento Rise (www.trentorise.eu/)
*
* All rights reserved. This program and the accompanying materials are made
* available under the terms of the GNU Lesser General Public License (LGPL)
* version 2.1 which accompanies this distribution, and is available at
*
* http://www.gnu.org/licenses/lgpl-2.1.html
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*
*******************************************************************************
*/

package eu.trentorise.opendata.ckanalyze.services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.ParamException;
import com.sun.jersey.api.client.ClientResponse.Status;

import eu.trentorise.opendata.ckanalyze.exceptions.JSONIZEDException;

@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {
    	
	@Override
    public Response toResponse(ParamException exception) {
		JSONIZEDException js = new JSONIZEDException();
		js.setErrorDescription(exception.getMessage());
        return Response.status(Status.BAD_REQUEST).entity(js).type(MediaType.APPLICATION_JSON).build();
    }
}