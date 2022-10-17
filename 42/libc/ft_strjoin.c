#include "libft.h"

char *ft_strjoin(char const *s1, char const *s2)
{
    char    *str;
    size_t  ls1;
    size_t  ls2;

    ls1 = ft_strlen(s1);
    ls2 = ft_strlen(s2);
    str = (char *)malloc(sizeof(char *) *(ls1 + ls2 + 1));
    if (!str || !s1 || !s2)
        return (NULL);
    ft_memcpy(str, s1, ls1);
    ft_memcpy(str + ls1, s2, ls2);
    str[ls1 + ls2 + 1] = '\0';
    return (str);
}
/*
#include <string.h>
#include <stdio.h>
int main (void)
{
	char csrc[100] = "hhkdjsuksncxmn";
    char csrc2[100] = "hhkdjsuksncxmn";

    // ft_memmove(csrc+3, csrc+2, 0);
    // memmove (csrc2+3, csrc2+2, 0);
    printf("%s\n", ft_strjoin("", ""));
    printf("%s", ft_strjoin2 ("", ""));
    return 0;
}*/