#include "./libft.h"

char    *ft_strnstr(const char *big, const char *little, size_t blen)
{
	size_t	i;
	size_t	size;
	char *src;

	i = 0;
	size = ft_strlen(little);
	src =(char *)big;
	if (!*little)
		return ((char *)big);
	while (src[i]  && size <= blen--)
	{
		if (src[i] == *little && !ft_memcmp(big, little, blen))
			return (&src[i]);
		i++;
	}
	return (0);
}

/*
int	main(void)
{
	char	*s="heyolloyo";
	char	*f="";
	printf("le resltat:%s \n",ft_strnstr(s, f, 6));
	//printf("le resltat:%s ",strnstr(s, f,6));
printf("%p\n", ft_strnstr("asdf qwerty", "qwerty",6));
//printf("%p\n", strnstr("asdf qwerty", "qwerty",6));

printf("%p\n", ft_strnstr("", "wer", 3));
//printf("%s\n", strnstr("", "wer", 3));

printf("%p\n", ft_strnstr("asdfzxcvrty", "zxcv", 7));
//printf("%p\n", strnstr("asdf qwerty", "zxcv", 7));
 }*/