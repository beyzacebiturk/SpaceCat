package com.beyzacebiturk.spacecat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SpaceCat extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture cat;
	Texture raven1;
	Texture raven2;
	Texture raven3;

	float catX = 0;
	float catY = 0;
	int gameState=0;
	float velocity=0;
	float gravity= 0.4f;
	float ravenVelocity=6;
	Random random;
	int score =0;
	int scoredenemy=0;
	BitmapFont font;
	BitmapFont font2;


	Circle catcircle;
	ShapeRenderer shapeRenderer;

	int numberOfEnemies=4;
	float[] ravenX = new float[numberOfEnemies];
	float[] enemyOffset = new float[numberOfEnemies];
	float[] enemyOffset2 = new float[numberOfEnemies];
	float[] enemyOffset3 = new float[numberOfEnemies];

	Circle[] enemycircles;
	Circle[] enemycircles2;
	Circle[] enemycircles3;

	float distance=0;


	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("forest-background.png");
		cat = new Texture("cat.png");
		raven1 = new Texture("raven.png");
		raven2 = new Texture("raven.png");
		raven3 = new Texture("raven.png");

		distance= Gdx.graphics.getWidth()/2;
		random = new Random();

		catX = Gdx.graphics.getWidth() / 10- cat.getHeight()/10;
		catY = Gdx.graphics.getHeight() / 5;

		shapeRenderer = new ShapeRenderer();

		catcircle = new Circle();
		enemycircles = new Circle[numberOfEnemies];
		enemycircles2 = new Circle[numberOfEnemies];
		enemycircles3 = new Circle[numberOfEnemies];

		font= new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);


		for (int i=0; i<numberOfEnemies; i++){
			ravenX[i]= Gdx.graphics.getWidth() - raven1.getWidth()/2 +i * distance;

			enemyOffset[i] = (random.nextFloat() - 0.5f)* Gdx.graphics.getHeight()-200;
			enemyOffset2 [i] = (random.nextFloat()- 0.5f)* Gdx.graphics.getHeight()-200;
			enemyOffset3[i] = (random.nextFloat()- 0.5f)* Gdx.graphics.getHeight()-200;

			enemycircles[i] = new Circle();
			enemycircles2[i] = new Circle();
			enemycircles3[i] = new Circle();
		}

	}

	@Override
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		if (gameState==1) {

			if(ravenX[scoredenemy] <Gdx.graphics.getWidth() / 10- cat.getHeight()/10){
				score++;
				if(scoredenemy<numberOfEnemies-1){
					scoredenemy++;
				}else{
					scoredenemy=0;
				}

			}

			if (Gdx.input.justTouched()) {
				velocity = -11;
			}

			for(int i=0; i<numberOfEnemies;i++){


				if(ravenX[i]< 0){

					ravenX[i]= ravenX[i]+ numberOfEnemies * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f)* Gdx.graphics.getHeight()-200;
					enemyOffset2 [i] = (random.nextFloat()- 0.5f)* Gdx.graphics.getHeight()-200;
					enemyOffset3[i] = (random.nextFloat()- 0.5f)* Gdx.graphics.getHeight()-200;


				} else{
					ravenX[i]= ravenX[i] - ravenVelocity;
				}

				batch.draw(raven1,ravenX[i], Gdx.graphics.getHeight()/2 + enemyOffset[i],Gdx.graphics.getHeight() / 4, Gdx.graphics.getWidth() / 5);
				batch.draw(raven2,ravenX[i], Gdx.graphics.getHeight()/2 + enemyOffset2[i],Gdx.graphics.getHeight() / 4, Gdx.graphics.getWidth() / 5);
				batch.draw(raven3,ravenX[i], Gdx.graphics.getHeight()/2 + enemyOffset3[i],Gdx.graphics.getHeight() / 4, Gdx.graphics.getWidth() / 5);


				enemycircles[i]= new Circle(ravenX[i]+Gdx.graphics.getHeight() / 8, +Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 8);
				enemycircles2[i]= new Circle(ravenX[i]+Gdx.graphics.getHeight() / 8, +Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 8);
				enemycircles3[i]= new Circle(ravenX[i]+Gdx.graphics.getHeight() / 8, +Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 8);

			}

			if (catY > 0){
				velocity= velocity + gravity;
				catY = catY - velocity;
			} else {
				gameState =2;
			}

		} else if(gameState==0){
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if(gameState==2){

			font2.draw(batch,"Game Over! Tap To Play Again!",450,Gdx.graphics.getHeight() / 2);

			if(Gdx.input.justTouched()){
				gameState=1;

				catY = Gdx.graphics.getHeight() / 5;

				for (int i=0; i<numberOfEnemies; i++){
					ravenX[i]= Gdx.graphics.getWidth() - raven1.getWidth()/2 +i * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f)* Gdx.graphics.getHeight()-200;
					enemyOffset2 [i] = (random.nextFloat()- 0.5f)* Gdx.graphics.getHeight()-200;
					enemyOffset3[i] = (random.nextFloat()- 0.5f)* Gdx.graphics.getHeight()-200;

					enemycircles[i] = new Circle();
					enemycircles2[i] = new Circle();
					enemycircles3[i] = new Circle();
				}
				velocity=0;
				scoredenemy=0;
				score=0;

			}
		}

		batch.draw(cat, catX, catY, Gdx.graphics.getHeight() / 5, Gdx.graphics.getWidth() / 8);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		catcircle.set(catX +Gdx.graphics.getHeight() / 10,catY+Gdx.graphics.getWidth() / 16, Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(catcircle.x,catcircle.y,catcircle.radius);



		for(int i =0; i<numberOfEnemies; i++){
			//shapeRenderer.circle(ravenX[i]+Gdx.graphics.getHeight() / 8, +Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 8);
			//shapeRenderer.circle(ravenX[i]+Gdx.graphics.getHeight() / 8, +Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 8);
			//shapeRenderer.circle(ravenX[i]+Gdx.graphics.getHeight() / 8, +Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 8);

			if(Intersector.overlaps(catcircle,enemycircles[i]) || Intersector.overlaps(catcircle,enemycircles2[i]) || Intersector.overlaps(catcircle,enemycircles3[i])) {
				gameState = 2;
			}
		}
		//shapeRenderer.end();
	}

	@Override
	public void dispose () {
	}

}