package org.sid.cinema_back.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj",types = Ticket.class)
public interface TicketProjection {
    /**
     * La projection permet de personaliser string data rest
     *
     */
    public Long getId();
    public String getNomClient();
    public double getPrix();
    public Integer getCodePayement();
    public boolean getReserve();
    public Place getPlace();

}
