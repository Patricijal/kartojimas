package org.example.kartojimas.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class MagicalCreature implements Serializable, Comparable<MagicalCreature> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Enumerated(EnumType.STRING)
    private CreatureType creatureType;
    private String elixirs;
    private LocalDate dateFound;
    private String wizard;
    private boolean isInDanger;

    public MagicalCreature() {
    }

    public MagicalCreature(int id, String title, CreatureType creatureType, String elixirs, LocalDate dateFound, String wizard, boolean isInDanger) {
        this.id = id;
        this.title = title;
        this.creatureType = creatureType;
        this.elixirs = elixirs;
        this.dateFound = dateFound;
        this.wizard = wizard;
        this.isInDanger = isInDanger;
    }

    public MagicalCreature(String title, CreatureType creatureType, String elixirs, LocalDate dateFound, String wizard, boolean isInDanger) {
        this.title = title;
        this.creatureType = creatureType;
        this.elixirs = elixirs;
        this.dateFound = dateFound;
        this.wizard = wizard;
        this.isInDanger = isInDanger;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public CreatureType getCreatureType() {
        return creatureType;
    }

    public String getElixirs() {
        return elixirs;
    }

    public LocalDate getDateFound() {
        return dateFound;
    }

    public String getWizard() {
        return wizard;
    }

    public boolean isInDanger() {
        return isInDanger;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatureType(CreatureType creatureType) {
        this.creatureType = creatureType;
    }

    public void setElixirs(String elixirs) {
        this.elixirs = elixirs;
    }

    public void setDateFound(LocalDate dateFound) {
        this.dateFound = dateFound;
    }

    public void setWizard(String wizard) {
        this.wizard = wizard;
    }

    public void setInDanger(boolean inDanger) {
        isInDanger = inDanger;
    }

    @Override
    public String toString() {
        return title + " " + wizard + " " + dateFound;
    }

    @Override
    public int compareTo(MagicalCreature o) {
        // First compare by dateFound
        if (this.dateFound.compareTo(o.dateFound) > 0) {
            return 1;
        } else if (this.dateFound.compareTo(o.dateFound) < 0) {
            return -1;
        } else {
            // Dates are equal, compare by title
            if (this.title.compareTo(o.title) > 0) {
                return 1;
            } else if (this.title.compareTo(o.title) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
//    public int compareTo(MagicalCreature o) {
//        if(this.title.compareTo(o.title) > 0) {
//            return 1;
//        } else if (this.dateFound.compareTo(o.dateFound) > 0) {
//            return -1;
//        } else {
//            return 0;
//        }
//    }
}
