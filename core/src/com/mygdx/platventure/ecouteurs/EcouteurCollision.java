package com.mygdx.platventure.ecouteurs;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class EcouteurCollision implements ContactListener {

    private Body gemmeEnContact;
    private boolean eauEnContact;
    private boolean pancarteDejaEnContact;

    @Override
    public void beginContact(Contact contact) {
        // En cas de contact entre le joueur et une gemme
        if (contact.getFixtureA().getBody().getUserData() == UserData.JOUEURP &&
                contact.getFixtureB().getBody().getUserData() == UserData.GEMME) {
            gemmeEnContact = contact.getFixtureB().getBody();
        } else if (contact.getFixtureB().getBody().getUserData() == UserData.JOUEURP &&
                contact.getFixtureA().getBody().getUserData() == UserData.GEMME) {
            gemmeEnContact = contact.getFixtureA().getBody();
        }

        // En cas de contact entre le joueur et de l'eau
        if (contact.getFixtureA().getBody().getUserData() == UserData.JOUEURP &&
                contact.getFixtureB().getBody().getUserData() == UserData.EAUW) {
            eauEnContact = true;
        } else if (contact.getFixtureB().getBody().getUserData() == UserData.JOUEURP &&
                contact.getFixtureA().getBody().getUserData() == UserData.EAUW) {
            eauEnContact = true;
        }

        // En cas de contact entre le joueur et la pancarte de sortie
        if (contact.getFixtureA().getBody().getUserData() == UserData.JOUEURP &&
                contact.getFixtureB().getBody().getUserData() == UserData.SORTIEZ) {
            pancarteDejaEnContact = true;
        } else if (contact.getFixtureB().getBody().getUserData() == UserData.JOUEURP &&
                contact.getFixtureA().getBody().getUserData() == UserData.SORTIEZ) {
            pancarteDejaEnContact = true;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public Body getGemmeEnContact() {
        return gemmeEnContact;
    }

    public void setGemmeEnContact(Body gemmeEnContact) {
        this.gemmeEnContact = gemmeEnContact;
    }

    public boolean isEauEnContact() {
        return eauEnContact;
    }

    public boolean isPancarteDejaEnContact() {
        return pancarteDejaEnContact;
    }
}
