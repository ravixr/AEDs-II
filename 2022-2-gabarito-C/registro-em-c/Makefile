debug:
	clear && gcc game.c -W -Wall -Werror -std=c99 -pedantic -o game.o && ./game.o < input.txt > output.txt

clean:
	clear && gcc game.c -std=c11 -o game.o && ./game.o

valgrind:
	clear && gcc game.c -W -Wall -Werror -std=c11 -pedantic -o game.o && valgrind --leak-check=yes --show-leak-kinds=all ./game.o < input.txt > output.txt