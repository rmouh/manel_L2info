#include "libft.h"

int ft_isalpha (int c)
{
    return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') ? 1 : 0);
}

/*
int main(int argc ,char argv[])
{
    printf("output : %d\n", ft_isalpha(9));
}*/