package com.web.api.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.web.api.beans.MessageFilterBean;
import com.web.api.model.Message;
import com.web.api.service.MessageService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(!(filterBean.getStart()<0) && filterBean.getSize() >0) {
			System.out.println("In here");
			return messageService.getAllMessagePaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message,@Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		String id = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri)
				.entity(newMessage)
				.build();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId,@Context UriInfo uriInfo) {
		Message message = messageService.getMessage(messageId);
		message.addLink(getUriForSelf(uriInfo, message), "Self");
		message.addLink(getUriForProfile(uriInfo, message), "Profile");
		message.addLink(getUriForComment(uriInfo, message), "Comment");
		return message;
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId")long id,Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}


	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
							.path(MessageResource.class)
							.path(Long.toString(message.getId()))
							.build()
							.toString();
		return uri;
	}
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
							.path(ProfileResource.class)
							.path(message.getAuthor())
							.build()
							.toString();
		return uri;
	}
	private String getUriForComment(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				            .path(MessageResource.class)
							.path(MessageResource.class,"getCommentResource")
							.path(CommentResource.class)
							.resolveTemplate("messageId", message.getId())
							.build()
							.toString();
		return uri;
	}
}
