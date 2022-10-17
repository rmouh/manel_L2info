#include "libft.h"

int intlenth(int n)
{
    int counter;

    counter = 0;
    while (n != 0)
    {
        n /= 10;
        counter++;
    }
    return (counter);
}

void fill_t(int n, char *p)
{
    if (n != 0)
    {
        *p = (n % 10 + '0');
        fill_t(n / 10, p-1);
    }
}

char *ft_itoa(int n)
{
    char    *c;
    int     size;
    
    if (n == 0)
        return (ft_strdup("0"));
    size = intlenth(n) + (n < 0);
    c = (char *)malloc(sizeof(char ) * size + 1);
    if (!c)
        return (NULL);
    fill_t((n > 0 ? n : -n), c + size - 1);
    if (n < 0)
        *c = '-';
    c[size] = '\0';
    return (c);
}
/*
#include <stdio.h>
int main (void){
    //printf ("%d\n",intlenth(-8473974) );
    char *c = ft_itoa(-78538573);
    // char p[10];
    // p = fill_t(10, c);
     printf ("%s\n",c);

}*/