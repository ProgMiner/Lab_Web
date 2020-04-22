#!/bin/bash

shopt -sq checkwinsize

FILES=(pismak0 pismak1 pismak2 pismak3 pismak4 pismak5 pismak6)

rnd=$(( RANDOM % ${#FILES[@]} ))

cat "$1/pismak/${FILES[$rnd]}.png.txt"
