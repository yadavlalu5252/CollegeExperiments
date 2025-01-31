# Import random module since we will be shuffling
import random

# Create lists to hold card faces, suits, and the deck
cardfaces = []
suits = ["Hearts", "Diamonds", "Clubs", "Spades"]
royals = ["J", "Q", "K", "A"]
deck = []

# Add number faces (2-10) to the cardfaces list
for i in range(2, 11):
    cardfaces.append(str(i))  # Adds numbers 2-10 as strings

# Add royal faces (J, Q, K, A) to the cardfaces list
for j in range(4):
    cardfaces.append(royals[j])

# Create the full deck by combining cardfaces with suits
for suit in suits:
    for face in cardfaces:
        card = face + " of " + suit  # Format each card as 'face of suit'
        deck.append(card)  # Add each card to the deck

# Shuffle the deck
random.shuffle(deck)

# Print all the shuffled cards
for card in deck:
    print(card)
