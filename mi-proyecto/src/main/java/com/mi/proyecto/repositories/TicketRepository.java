/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.repositories;

import com.mi.proyecto.domain.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manon
 */
public class TicketRepository {
    private List<Ticket> tickets;
    
    public TicketRepository() {
        tickets = new ArrayList<>();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public boolean agregarTicket(Ticket ticket){
       ticket.setId(tickets.size());
       return tickets.add(ticket);
    }
    
    public Ticket buscarPorId(int id) {
        for (Ticket t: tickets) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    
    @Override
    public String toString(){
        String resultado;
        if(tickets.isEmpty()){
            resultado = "No hay tickets registrados";
        }else{
            resultado = "";
            for(Ticket t : tickets){
                resultado += "- " + t.toString() + "\n";
            }
        }
        return resultado;
    }
}
