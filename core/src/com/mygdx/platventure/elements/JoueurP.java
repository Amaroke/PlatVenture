package com.mygdx.platventure.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class JoueurP extends Element {

    private final PolygonShape formeTete = new PolygonShape();
    private final CircleShape formePied = new CircleShape();

    public JoueurP(Vector2 position) {
        super(position);
        Vector2[] vecteurs = {
                new Vector2(0, 0.5f),
                new Vector2(0.25f, 0),
                new Vector2(0, -0.25f),
                new Vector2(-0.25f, 0)
        };
        this.formeTete.set(vecteurs);
        this.formePied.setRadius(0.125f);
        this.formePied.setPosition(new Vector2(0, -0.375f));
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        // L'element de peut se déplacer
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Il ne peut pas faire de rotation, et donc ne peut pas tomber
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(this.position);
    }

    @Override
    public void setFixture() {
        if ((this.bodyDef != null) && (this.body != null)) {
            FixtureDef fixtureDefTete = new FixtureDef();
            fixtureDefTete.shape = this.formeTete;
            fixtureDefTete.density = 0.5f;
            fixtureDefTete.restitution = 0.1f;
            fixtureDefTete.friction = 0.25f;
            getBody().createFixture(fixtureDefTete);

            FixtureDef fixtureDefPied = new FixtureDef();
            fixtureDefPied.shape = this.formePied;
            fixtureDefPied.density = 0.5f;
            fixtureDefPied.restitution = 0.1f;
            fixtureDefPied.friction = 0.25f;
            getBody().createFixture(fixtureDefPied);

            getBody().setTransform(new Vector2(getPosition().x + 0.5f, getPosition().y + 0.5f), 0);
        }
        this.formeTete.dispose();
        this.formePied.dispose();
    }

    public void setMouvevement(Vector2 v) {
        // On met "< 0.00001" au lieu de "== 0", pour plus de liberté de mouvement
        if (this.getBody().getLinearVelocity().y < 0.00001f && this.getBody().getLinearVelocity().y > -0.00001f) {
            this.getBody().applyForceToCenter(v, true);
        }
    }
}
