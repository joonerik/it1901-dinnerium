import React, { useState } from 'react';
//import data from '../../../../core/src/main/resources/dinnerium/storage/data.json';
//import data from '../data.json';
import axios from 'axios';
import './recipeBox.scss';

const RecipeBoxComponent = () => {
  //const [isUser, setUser] = useState({});
  const data = [
    {
      ingredientContainer: {
        ingredients: [
          {
            quantity: {
              unit: 'gram',
              amount: 400.0,
            },
            name: 'minced meat',
          },
          {
            quantity: {
              unit: 'gram',
              amount: 200.0,
            },
            name: 'cheese',
          },
          {
            quantity: {
              unit: 'stk',
              amount: 9.0,
            },
            name: 'lasagne plates',
          },
        ],
      },
      recipeInstructions: ['cook', 'bake', 'eat'],
      metadata: {
        author: 'bestUsername',
        portion: 4.0,
        image: 'http://folk.ntnu.no/anderobs/images/tikkaMasala.png',
        recipeName: 'Lasagne',
        recipeDescription: 'God og smakfull lasagne',
        minutes: 90,
      },
    },
    {
      ingredientContainer: {
        ingredients: [
          {
            quantity: {
              unit: 'stk',
              amount: 2.0,
            },
            name: 'eggs',
          },
          {
            quantity: {
              unit: 'dl',
              amount: 3.0,
            },
            name: 'tikka',
          },
        ],
      },
      recipeInstructions: ['mix', 'doStuff', 'serve'],
      metadata: {
        author: 'bestUsername',
        portion: 2.0,
        image: 'http://folk.ntnu.no/anderobs/images/tikkaMasala.png',
        recipeName: 'Tikka masala',
        recipeDescription: 'Describing description of tikka masala',
        minutes: 60,
      },
    },
    {
      ingredientContainer: {
        ingredients: [],
      },
      recipeInstructions: [],
      metadata: {
        author: 'username',
        portion: 32.0,
        image: 'http://folk.ntnu.no/anderobs/images/tikkaMasala.png',
        recipeName: 'name',
        recipeDescription: 'dfs',
        minutes: 2,
      },
    },
  ];

  return (
    <div>
      {data.map((item, index) => (
        <div className="recipeBox">
          <h1>{item.metadata.recipeName}</h1>
          <div className="recipeBoxDescription">
            <p>
              <span>Something missing bro</span> |
              <span> {item.metadata.minutes} minutes required</span>
            </p>
            <p>
              <span>Author: {item.metadata.author} | </span>
              <span>Portions: {item.metadata.portion}</span>
            </p>
            <div id="recipeContainer">
              {item.recipeInstructions.map((instruction, index) => (
                <li>{instruction}</li>
              ))}
            </div>
            <div id="ingredientContainer">
              {item.ingredientContainer.ingredients.map((ingredient, index) => (
                <li>
                  {ingredient.name} {ingredient.quantity.amount}{' '}
                  {ingredient.quantity.unit}
                </li>
              ))}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default RecipeBoxComponent;
